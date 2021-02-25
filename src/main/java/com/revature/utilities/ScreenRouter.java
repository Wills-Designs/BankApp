package com.revature.utilities;
import com.revature.screens.Screen;

/** class used to route user to different screens */
public class ScreenRouter {

    private Set<Screen> screens = new Set<>();
    public Set<Screen> getScreens(){
        return screens;
    }

    /**
     * method used to add screens into our set
     * @param screen
     * @return
     */
    public ScreenRouter addScreen(Screen screen) {
        screens.add(screen);
        return this;
    }

    /**
     * checks if the route matches an existing screen, if it is then it is rendered on the console
     * @param route
     */
    public void navigate(String route){
        for (Screen screen : screens.toArray(Screen.class)) {
            if (screen.getRoute().equals(route)) {
                screen.render();
            }
        }
    }
}





































