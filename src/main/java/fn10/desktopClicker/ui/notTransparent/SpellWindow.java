package fn10.desktopClicker.ui.notTransparent;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import fn10.desktopClicker.game.GameManager;
import fn10.desktopClicker.game.SavedGame;
import fn10.desktopClicker.game.spells.DoubleProfitSpell;
import fn10.desktopClicker.game.spells.SummonCoinSpell;
import fn10.desktopClicker.game.spells.interfaces.IBaseSpell;
import fn10.desktopClicker.game.spells.interfaces.ICharageableSpell;
import fn10.desktopClicker.game.spells.interfaces.ISpell;
import fn10.desktopClicker.game.spells.interfaces.ISpellWithActiveText;
import fn10.desktopClicker.util.ImageUtilites;
import fn10.desktopClicker.util.Various;

public class SpellWindow extends JDialog {

    private final JPanel InnerScroll = new JPanel();
    private final JScrollPane ScrollPane = new JScrollPane(InnerScroll, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private final SpringLayout Lay = new SpringLayout();
    private final BoxLayout InnerLay = new BoxLayout(InnerScroll, BoxLayout.Y_AXIS);

    private final IBaseSpell[] spells = new IBaseSpell[] {
            new SummonCoinSpell(),
            new DoubleProfitSpell(),
    };

    public static void showSpells() {
        new SpellWindow().setVisible(true);
    }

    private SpellWindow() {
        super(null, "Spells", ModalityType.APPLICATION_MODAL);

        //GameManager.Paused = true; dont pause so things are better

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                GameManager.Paused = false;
            }
        });

        setSize(605, 500);
        setResizable(false);

        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setAlwaysOnTop(true);
        setLayout(Lay);

        ScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        ScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setLocation(ImageUtilites.getScreenCenter(this));

        Lay.putConstraint(SpringLayout.EAST, ScrollPane, -5, SpringLayout.EAST, getContentPane());
        Lay.putConstraint(SpringLayout.WEST, ScrollPane, 5, SpringLayout.WEST, getContentPane());
        Lay.putConstraint(SpringLayout.NORTH, ScrollPane, 5, SpringLayout.NORTH, getContentPane());
        Lay.putConstraint(SpringLayout.SOUTH, ScrollPane, -5, SpringLayout.SOUTH, getContentPane());

        InnerScroll.setLayout(InnerLay);

        for (IBaseSpell spell : spells) {
            try {
                SpringLayout lay = new SpringLayout();
                JPanel panel = new JPanel();
                JLabel image = new JLabel(spell.getSpellImage());

                JLabel text = new JLabel(
                        "<html> <b>" + spell.getSpellName() + "</b> <br><br> " + spell.getSpellDescription()
                                + "</html>");

                JButton button = new JButton((spell instanceof ICharageableSpell ? "Hold to Charge"
                        : spell instanceof ISpell
                                ? "Mana cost: " + ((ISpell) spell).getManaRequirment(GameManager.CurrentGame)
                                : ""));

                text.setForeground(Color.white);

                image.setLayout(lay);

                lay.putConstraint(SpringLayout.WEST, text, 10, SpringLayout.WEST, image);
                lay.putConstraint(SpringLayout.EAST, text, -300, SpringLayout.EAST, image);
                lay.putConstraint(SpringLayout.NORTH, text, 5, SpringLayout.NORTH, image);

                lay.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, text);
                lay.putConstraint(SpringLayout.SOUTH, button, -5, SpringLayout.SOUTH, image);
                lay.putConstraint(SpringLayout.EAST, button, 0, SpringLayout.EAST, text);

                if (spell instanceof ICharageableSpell) {
                    JLabel maxMana = new JLabel("Max Mana Cost: " + GameManager.CurrentGame.Mana,
                            SwingConstants.RIGHT);
                    maxMana.setForeground(Color.white);
                    JProgressBar chargeBar = new JProgressBar(0, 100);

                    lay.putConstraint(SpringLayout.EAST, chargeBar, -10, SpringLayout.EAST, image);
                    lay.putConstraint(SpringLayout.SOUTH, chargeBar, -5, SpringLayout.SOUTH, image);
                    lay.putConstraint(SpringLayout.NORTH, chargeBar, 0, SpringLayout.NORTH, button);
                    lay.putConstraint(SpringLayout.WEST, chargeBar, 5, SpringLayout.EAST, button);

                    lay.putConstraint(SpringLayout.SOUTH, maxMana, -5, SpringLayout.NORTH, chargeBar);
                    lay.putConstraint(SpringLayout.EAST, maxMana, 0, SpringLayout.EAST, chargeBar);

                    button.addMouseListener(new MouseAdapter() {
                        private Instant pressedTime = null;
                        private boolean isPressing = false;
                        private float manaCost = 0;
                        {
                            new Timer().scheduleAtFixedRate(new TimerTask() {

                                @Override
                                public void run() {
                                    maxMana.setText("Max Mana Cost: " + GameManager.CurrentGame.Mana);
                                    button.setEnabled(!(GameManager.CurrentGame.Mana <= 0));
                                    if (manaCost >= GameManager.CurrentGame.Mana) {
                                        manaCost = GameManager.CurrentGame.Mana;
                                        return;
                                    }
                                    if (!isPressing)
                                        return;

                                    float heldTime = (Duration.between(pressedTime, Instant.now()).toMillis() / 1000f)
                                            * (GameManager.CurrentGame.ManaChargeMulipler / 15);

                                    System.out.println(manaCost);

                                    SavedGame game = GameManager.CurrentGame;
                                    manaCost = ((ICharageableSpell) spell).getManaRequirment(game, heldTime);

                                    chargeBar.setValue(
                                            (int) ((Math.max(((ICharageableSpell) spell).getManaRequirment(game,
                                                    heldTime), 0.00000000001) / game.Mana) * 100));

                                }

                            }, 1, 1);
                        }

                        public void mousePressed(MouseEvent e) {
                            if (!e.isPopupTrigger() && button.isEnabled()) {
                                pressedTime = Instant.now();
                                isPressing = true;
                            }
                        }

                        public void mouseReleased(MouseEvent e) {
                            if (!e.isPopupTrigger() && button.isEnabled()) {
                                isPressing = false;
                                int option = JOptionPane.showConfirmDialog(button,
                                        "Spend " + manaCost + " mana to cast this spell?", "Cast Spell?",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (option == JOptionPane.YES_OPTION) {
                                    ((ICharageableSpell) spell).cast(GameManager.CurrentGame, manaCost);
                                    GameManager.CurrentGame.Mana -= manaCost;
                                }
                            }
                        }
                    });

                    image.add(maxMana);
                    image.add(chargeBar);
                } else if (spell instanceof ISpell) {
                    button.addActionListener(ac -> {
                        SavedGame game = GameManager.CurrentGame;
                        if (((ISpell) spell).canCast(game) && ((ISpell) spell).getManaRequirment(game) <= game.Mana) {
                            ((ISpell) spell).cast(game);
                        } else {
                            Various.playSound(getClass().getResource("/cannotBuy.wav"));
                        }
                    });
                }

                if (spell instanceof ISpellWithActiveText) {
                    JLabel activeText = new JLabel(((ISpellWithActiveText)spell).getString());

                    activeText.setForeground(Color.white);

                    lay.putConstraint(SpringLayout.EAST, activeText, -10, SpringLayout.EAST, image);
                    lay.putConstraint(SpringLayout.SOUTH, activeText, -5, SpringLayout.SOUTH, image);
                    lay.putConstraint(SpringLayout.NORTH, activeText, 0, SpringLayout.NORTH, button);
                    lay.putConstraint(SpringLayout.WEST, activeText, 5, SpringLayout.EAST, button);

                    new Timer().scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {
                            if (!activeText.getText().equals(((ISpellWithActiveText)spell).getString())) {
                                activeText.setText(((ISpellWithActiveText)spell).getString());
                            }
                        }
                        
                    }, 2, 1);

                    image.add(activeText);
                }

                image.add(button);
                image.add(text);

                panel.add(image);

                panel.setAlignmentX(0.5f);

                InnerScroll.add(panel);
                InnerScroll.add(Box.createHorizontalStrut(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        add(ScrollPane);
    }

}
