package mix_db.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;

import javax.swing.JFrame;

import mix_db.core.Session;
import mix_db.core.exceptions.WrongCredentialsException;
import mix_db.data.dao.User;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.model.Model;
import mix_db.view.ExceptionPanel;
import mix_db.view.login.LoginPanel;
import mix_db.view.login.LoginView;
import mix_db.view.login.SignInPanel;

/**
 * application controller
 */
public class LoginController {

    private final JFrame view;

    private Model model;

    /**
     * constructor
     */
    public LoginController() {
        this.view = new LoginView();

        try {
            final Connection connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password");
            this.model = new DbModel(connection);

        } catch (Exception e) {
            e.printStackTrace();
            new ExceptionPanel("Problema connessione con database SQL", view);
        }

        if(this.view instanceof LoginView loginView){
            if(loginView.getMainPanel() instanceof LoginPanel loginPanel) {

                loginPanel.verifyLogin(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            manageLoginAttempt(); 
                        } catch (WrongCredentialsException ex) {
                            new ExceptionPanel(ex, view);
                            return;
                        }
                    }
                });

                loginPanel.requestSignIn(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        requestedSignIn(loginView);
                    }
                });

                loginPanel.requestToEnterAsGuest(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        requestedToEnterAsGuest();
                    }
                    
                });
            }
        }
    }

    /**
     * manages login attempt
     */
    private void manageLoginAttempt() {
        if(this.view instanceof LoginView loginView){
            if(loginView.getMainPanel() instanceof LoginPanel loginPanel) {
                final String email = loginPanel.getEmail();
                final String password = loginPanel.getPassword();

                if(Session.getInstance().login(email, password)) {
                    loginView.dispose();
                    new ApplicationController();
                } else {
                    // *login failed
                    throw new WrongCredentialsException("Email o password errati");
                }
            }
        }
    }

    /**
     * tries to sign in
     * @param v the current JFrame
     */
    private void requestedSignIn(LoginView v) {
        v.setMainPanel(new SignInPanel());     

        if(v.getMainPanel() instanceof SignInPanel sp) {
            sp.requestSignIn(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        manageSignInAttempt();
                    } catch (Exception exception) {
                        new ExceptionPanel(exception, v);
                        return;
                    }
                }
            });
        }
    }

    /**
     * enters without an account
     */
    private void requestedToEnterAsGuest() {
        this.view.dispose();
        Session.getInstance().setLoggedUser(null);
        new ApplicationController();
    }

    /**
     * manages sign in attempt
     */
    private void manageSignInAttempt() {
        if(this.view instanceof LoginView loginView){
            if(loginView.getMainPanel() instanceof SignInPanel signInPanel) {
                final String email = signInPanel.getEmail();
                final String password = signInPanel.getPassword();
                final String name = signInPanel.getName();
                final String surname = signInPanel.getSurname();
                final Date birthDate;
                
                if(signInPanel.getBirthDate().isPresent()) {
                birthDate = signInPanel.getBirthDate().get();
                } else {
                    throw new NumberFormatException("formato data non valido");
                }

                final var u = new User(0, email, password, name, surname, 
                    birthDate, mix_db.data.Role.USER, null, 0, 0, 0);


                if(this.model.registerUser(u).isEmpty()) {
                    throw new WrongCredentialsException("errore nella registrazione");
                } else {
                    final var actualUser = model.login(email, password);

                    if(actualUser.isPresent()) {
                        Session.getInstance().setLoggedUser(actualUser.get());
                        this.view.dispose();
                        new ApplicationController();
                    }
                    else {
                        throw new WrongCredentialsException("errore nella registrazione");
                    }
                }
            }
        }
    }
}