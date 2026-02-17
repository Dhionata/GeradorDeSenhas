package main

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.DefaultListCellRenderer
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.border.EmptyBorder
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.AbstractDocument
import javax.swing.text.AttributeSet
import javax.swing.text.DocumentFilter

class UserInterface private constructor(private val service: IPasswordService) {
    private val rootPane = JPanel(BorderLayout(15, 15))
    private val textNumber = JTextField()
    private val comboBox = JComboBox(PasswordType.entries.toTypedArray())
    private val statusLabel = JLabel("Aguardando entrada...", JLabel.CENTER)
    private val strengthLabel = JLabel(" ", JLabel.CENTER)

    init {
        setupLookAndFeel()
        setupUI()
        setupListeners()
        setupInputFilter()
    }

    private fun setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (_: Exception) {
        }
    }

    private fun setupInputFilter() {
        (textNumber.document as AbstractDocument).documentFilter = object : DocumentFilter() {
            override fun insertString(fb: FilterBypass, offset: Int, string: String, attr: AttributeSet?) {
                if (string.all { it.isDigit() }) super.insertString(fb, offset, string, attr)
            }

            override fun replace(fb: FilterBypass, offset: Int, length: Int, text: String, attrs: AttributeSet?) {
                if (text.all { it.isDigit() }) super.replace(fb, offset, length, text, attrs)
            }
        }
    }

    private fun setupUI() {
        rootPane.border = EmptyBorder(30, 30, 30, 30)
        rootPane.background = Color(250, 250, 250)

        val header = JLabel("Gerador de Senhas")
        header.font = Font("Segoe UI", Font.BOLD, 24)
        header.foreground = Color(44, 62, 80)
        header.horizontalAlignment = JLabel.CENTER
        rootPane.add(header, BorderLayout.NORTH)

        val centerPanel = JPanel(GridBagLayout())
        centerPanel.isOpaque = false
        val gbc = GridBagConstraints().apply {
            fill = GridBagConstraints.HORIZONTAL
            insets = Insets(8, 0, 8, 0)
            gridx = 0
        }

        fun addComponent(label: String, component: JComponent, row: Int) {
            gbc.gridy = row * 2
            centerPanel.add(JLabel(label).apply { font = Font("Segoe UI", Font.PLAIN, 13) }, gbc)
            gbc.gridy = row * 2 + 1
            component.font = Font("Segoe UI", Font.PLAIN, 15)
            centerPanel.add(component, gbc)
        }

        addComponent("Quantidade de caracteres:", textNumber, 0)

        comboBox.setRenderer(object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(l: JList<*>?, v: Any?, i: Int, s: Boolean, f: Boolean): Component {
                return super.getListCellRendererComponent(l, (v as? PasswordType)?.description, i, s, f)
            }
        })
        addComponent("Tipo de caracteres permitidos:", comboBox, 1)

        // Strength Display
        strengthLabel.font = Font("Segoe UI", Font.BOLD, 12)
        gbc.gridy = 4
        centerPanel.add(strengthLabel, gbc)

        rootPane.add(centerPanel, BorderLayout.CENTER)

        statusLabel.font = Font("Segoe UI", Font.ITALIC, 13)
        statusLabel.border = EmptyBorder(10, 0, 0, 0)
        rootPane.add(statusLabel, BorderLayout.SOUTH)
    }

    private fun setupListeners() {
        val updateAction = {
            val type = comboBox.selectedItem as PasswordType
            service.generateAndCopy(textNumber.text, type)
                .onSuccess { (_, strength) ->
                    statusLabel.text = "✓ Copiada para o clipboard!"
                    statusLabel.foreground = Color(39, 174, 96)
                    strengthLabel.text = "Tempo estimado para quebrar: $strength"

                    // Lógica de cor baseada na unidade de tempo
                    strengthLabel.foreground = when {
                        strength.contains("anos") || strength.contains("e+") -> Color(39, 174, 96) // Verde (Seguro)
                        strength.contains("dias") -> Color(211, 84, 0) // Laranja (Médio)
                        else -> Color(192, 57, 43) // Vermelho (Fraco)
                    }
                }
                .onFailure { e ->
                    strengthLabel.text = " "
                    if (textNumber.text.isNotEmpty()) {
                        statusLabel.text = e.message
                        statusLabel.foreground = Color(192, 57, 43)
                    } else {
                        statusLabel.text = "Aguardando entrada..."
                        statusLabel.foreground = Color(127, 140, 141)
                    }
                }
            Unit
        }

        textNumber.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent) {
                updateAction()
            }

            override fun removeUpdate(e: DocumentEvent) {
                updateAction()
            }

            override fun changedUpdate(e: DocumentEvent) {
                updateAction()
            }
        })

        comboBox.addActionListener { updateAction() }
    }

    companion object {
        fun start() {
            SwingUtilities.invokeLater {
                val service = PasswordService()
                val ui = UserInterface(service)
                JFrame("Gerador de Senhas").apply {
                    contentPane = ui.rootPane
                    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
                    pack()
                    minimumSize = Dimension(450, 450)
                    setLocationRelativeTo(null)
                    isVisible = true
                }
            }
        }
    }
}
