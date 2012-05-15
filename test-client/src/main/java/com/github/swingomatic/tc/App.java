/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.swingomatic.tc;

import com.github.swingomatic.tc.controller.Main;

/**
 *
 * @author moscac
 */
public class App {
  
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {

        Main main = new Main();
        main.init(true);

    }
  
}
