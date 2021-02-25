package com.project.utilities;

import com.project.repos.UserRepository;
import com.project.screens.BankScreen;
import com.project.screens.HomeScreen;
import com.project.screens.RegisterScreen;
import com.project.users.UserService;
import com.project.screens.LoginScreen;
import com.project.screens.WithdrawScreen;
import com.project.screens.DepositScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** contains all the important tools to ensure app functions
 * such as all the screens, as well as opening the Buffered reader for user input
 * */
public class AppState {

    private BufferedReader console;
    private ScreenRouter router;
    private Session currentSession;
    private boolean appRunning;

    /**
     * opens bufferred reader and adds the screens
     */
    public AppState() {

        System.out.println("[LOG] - Initializing application...");

        this.appRunning = true;
        this.console = new BufferedReader(new InputStreamReader(System.in));

        final UserRepository userRepo = new UserRepository();
        final UserService userService = new UserService(userRepo);

        router = new ScreenRouter();
        router.addScreen(new HomeScreen())
                .addScreen(new RegisterScreen(userService))
                .addScreen(new LoginScreen(userService))
                .addScreen(new BankScreen(userService))
                .addScreen(new WithdrawScreen(userService))
                .addScreen(new DepositScreen(userService));

        System.out.println("[LOG] - Application initialized");
    }

    public BufferedReader getConsole() {
        return console;
    }

    public ScreenRouter getRouter() {
        return router;
    }

    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public void invalidateCurrentSession() {
        this.currentSession = null;
    }

    public boolean isSessionValid() {
        return (this.currentSession != null);
    }
}