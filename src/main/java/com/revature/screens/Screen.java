package com.revature.screens;

/** the abstract parent class that
 * allows us to set names and routes for all our sub-class screens
 */
public abstract class Screen {
    protected String name;
    protected String route;

    /**
     * sets screen name and route
     * @param name
     * @param route
     */
    public Screen(String name, String route) {
        this.name = name;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }
    /** render method is used to generate what is going to be displayed on our console */
    public abstract void render();
}
