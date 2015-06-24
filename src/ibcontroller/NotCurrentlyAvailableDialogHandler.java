// This file is part of the "IBController".
// Copyright (C) 2004 Steven M. Kearns (skearns23@yahoo.com )
// Copyright (C) 2004 - 2011 Richard L King (rlking@aultan.com)
// For conditions of distribution and use, see copyright notice in COPYING.txt

// IBController is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// IBController is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with IBController.  If not, see <http://www.gnu.org/licenses/>.

package ibcontroller;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;

class NotCurrentlyAvailableDialogHandler implements WindowHandler {
    public boolean filterEvent(Window window, int eventId) {
        switch (eventId) {
            case WindowEvent.WINDOW_OPENED:
            case WindowEvent.WINDOW_ACTIVATED:
                return true;
            default:
                return false;
        }
    }

    public void handleWindow(Window window, int eventID) {
        if (! Utils.clickButton(window, "OK")) {
            Utils.logError("The system is not currently available.");
            return;
        }

        if (TwsListener.getLoginFrame() != null) {
            JButton button2 =
                    Utils.findButton(TwsListener.getLoginFrame(), "Login");
            button2.requestFocus();
            KeyEvent ke =
                     new KeyEvent(button2, KeyEvent.KEY_PRESSED,
                                  System.currentTimeMillis(),
                                  KeyEvent.ALT_DOWN_MASK,
                                  KeyEvent.VK_F4,
                                  KeyEvent.CHAR_UNDEFINED);
            button2.dispatchEvent(ke);
        }
    }

    public boolean recogniseWindow(Window window) {
        if (! (window instanceof JDialog)) return false;

        return (Utils.titleContains(window, "Login") &&
                Utils.findLabel(window, "not currently available") != null);
    }
}

