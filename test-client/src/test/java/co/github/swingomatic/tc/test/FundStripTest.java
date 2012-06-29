/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.github.swingomatic.tc.test;

import com.github.swingomatic.message.ApplicationCommand;
import com.github.swingomatic.message.ComponentInfo;
import com.github.swingomatic.tc.Client;
import com.github.swingomatic.tc.http.HttpClientWrapper;
import com.thoughtworks.xstream.XStream;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Carl J. Mosca
 */
public class FundStripTest {

    /**
     * The Constant LIST_COMPONENTS_URL.
     */
    private final static String LIST_COMPONENTS_URL = "http://localhost:8088/list-components";
    private final static String MAIN_URL = "http://localhost:8088";
    private final String BEGINNING_OF_MESSAGE_TAG = "<com.github.swingomatic.message.ApplicationCommand>";
    /**
     * The http client.
     */
    private Client client;

    @Test
    public void testAccount() throws Exception {
        Client client = new Client();
        XStream xstream = new XStream();
        ApplicationCommand ac = new ApplicationCommand();
        ac.setCommand("execute");
        ac.setComponents(new ArrayList(0));

        ComponentInfo componentInfo;

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.subsystem.desktop.menus.ApplicationMenu");
        componentInfo.setDelay(0);
        componentInfo.setRetries(0);
        componentInfo.setText("Applications");
        componentInfo.setxCoordinate(33);
        componentInfo.setyCoordinate(1);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.subsystem.spending.ar.ui.ArMenu");
        componentInfo.setDelay(0);
        componentInfo.setRetries(0);
        componentInfo.setText("Accounts Receivable");
        componentInfo.setxCoordinate(1);
        componentInfo.setyCoordinate(45);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.MenuItem");
        componentInfo.setDelay(0);
        componentInfo.setRetries(0);
        componentInfo.setText("New Form...");
        componentInfo.setToolTipText("Select to open new form");
        componentInfo.setxCoordinate(1);
        componentInfo.setyCoordinate(3);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        // Values for the Create New Form Dialog
        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.ComboBox");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Doc Type:");
        componentInfo.setRetries(0);
        componentInfo.setText("CB - Cash Receipt - Bankruptcy");
        componentInfo.setToolTipText("Select a document type");
        componentInfo.setxCoordinate(112);
        componentInfo.setyCoordinate(8);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.ComboBox");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Security Org:");
        componentInfo.setRetries(0);
        componentInfo.setText("BANKCLK");
        componentInfo.setToolTipText("Select the security organization responsible for this form");
        componentInfo.setxCoordinate(112);
        componentInfo.setyCoordinate(56);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.TextField");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Document Title:");
        componentInfo.setRetries(0);
        componentInfo.setText("1212 Receipts");
        componentInfo.setToolTipText("Enter a document title");
        componentInfo.setxCoordinate(112);
        componentInfo.setyCoordinate(80);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.Button");
        componentInfo.setDelay(0);
        componentInfo.setRetries(0);
        componentInfo.setText("OK");
        componentInfo.setToolTipText("Accept values and create a form");
        componentInfo.setxCoordinate(358);
        componentInfo.setyCoordinate(8);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);


        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.TextField");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Received By:");
        componentInfo.setRetries(0);
        componentInfo.setText("Lisa");
        componentInfo.setToolTipText("Enter the name of the person who this form was Received By");
        componentInfo.setxCoordinate(114);
        componentInfo.setyCoordinate(24);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.TextField");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Deposit #:");
        componentInfo.setRetries(0);
        componentInfo.setText("12/12/12");
        componentInfo.setToolTipText("Enter deposit number");
        componentInfo.setxCoordinate(118);
        componentInfo.setyCoordinate(52);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.TextField");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Receipt Date:");
        componentInfo.setRetries(0);
        componentInfo.setText("1");
        componentInfo.setToolTipText("Enter the document date (right mouse or Shift-F10 for list)");
        componentInfo.setxCoordinate(150);
        componentInfo.setyCoordinate(0);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.TextField");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Acctg Period:");
        componentInfo.setRetries(0);
        componentInfo.setText("11/11");
        componentInfo.setToolTipText("Accounting period");
        componentInfo.setxCoordinate(296);
        componentInfo.setyCoordinate(24);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.system.ui.TextFieldSearchable");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Reporting Acctg Period:");
        componentInfo.setRetries(0);
        componentInfo.setText("11/11");
        componentInfo.setToolTipText("Enter the reporting accounting period (right mouse button or Shift-F10 for list");
        componentInfo.setxCoordinate(150);
        componentInfo.setyCoordinate(48);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.system.ui.TextFieldSearchable");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Disbursing Office:");
        componentInfo.setRetries(0);
        componentInfo.setText("8683");
        componentInfo.setToolTipText("Enter disbursing office(right mouse or Shift-F10 for list)");
        componentInfo.setxCoordinate(549);
        componentInfo.setyCoordinate(172);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.TextField");
        componentInfo.setDelay(0);
        componentInfo.setOfLabel("Accomplished Date:");
        componentInfo.setRetries(0);
        componentInfo.setText("11/11/11");
        componentInfo.setToolTipText("Enter accomplished date (right mouse or Shift-F10 for list)");
        componentInfo.setxCoordinate(549);
        componentInfo.setyCoordinate(196);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.ArrowTabbedPane");
        componentInfo.setDelay(3000);
        componentInfo.setRetries(0);
        componentInfo.setText("");
        componentInfo.setTitle("Accounting Lines");
        componentInfo.setxCoordinate(0);
        componentInfo.setyCoordinate(0);
        componentInfo.setRequestFocus(false);
        componentInfo.setiValue(1);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.widgets.ComboBox");
        componentInfo.setDelay(300);
        componentInfo.setOfLabel("Line Type:");
        componentInfo.setRetries(3);
        componentInfo.setText("Normal");
        componentInfo.setToolTipText("Select line type");
        componentInfo.setxCoordinate(110);
        componentInfo.setyCoordinate(45);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setOfLabel("");
        componentInfo.setClazz("class com.ams.momentum.system.ui.MoneyTextField");
        componentInfo.setDelay(300);
        componentInfo.setOfLabel("Principal Amount:");
        componentInfo.setRetries(3);
        componentInfo.setText("100.00");
        componentInfo.setToolTipText("Enter the Principal Amount The current input is: $0.00");
        componentInfo.setxCoordinate(444);
        componentInfo.setyCoordinate(44);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setClazz("class com.ams.momentum.system.ui.TextFieldSearchable");
        componentInfo.setDelay(500);
        componentInfo.setOfLabel("Transaction Type:");
        componentInfo.setRetries(3);
        componentInfo.setText("04");
        componentInfo.setToolTipText("Enter the Transaction Type(right mouse or Shift-F10 for list)");
        componentInfo.setxCoordinate(110);
        componentInfo.setyCoordinate(72);
        componentInfo.setRequestFocus(true);
        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setClazz("class com.ams.momentum.widgets.Button");
        componentInfo.setDelay(1000);
        componentInfo.setRetries(3);
        componentInfo.setText("Funding...");
        componentInfo.setToolTipText("Select to enter the principal accounting strip information");
        componentInfo.setxCoordinate(194);
        componentInfo.setyCoordinate(72);
        componentInfo.setRequestFocus(true);
        componentInfo.setiValue(100);
        ac.getComponents().add(componentInfo);

//        componentInfo = new ComponentInfo();
//        componentInfo.setOfLabel("");
//        componentInfo.setClazz("class com.ams.momentum.subsystem.common.ui.AccountingStripDialog");
//        componentInfo.setDelay(0);
//        componentInfo.setName("dialog0");
//        componentInfo.setRetries(0);
//        componentInfo.setText("");
//        componentInfo.setTitle("Accounting Strip");
//        componentInfo.setToolTipText("");
//        componentInfo.setxCoordinate(469);
//        componentInfo.setyCoordinate(321);
//        componentInfo.setRequestFocus(true);
//        componentInfo.setiValue(0);
//        ac.getComponents().add(componentInfo);

        componentInfo = new ComponentInfo();
        componentInfo.setClazz("class com.ams.momentum.system.ui.TextFieldSearchable");
        componentInfo.setDelay(2000);
        componentInfo.setOfLabel("Template");
//        componentInfo.setOfLabel("Org");
        componentInfo.setRetries(5);
        componentInfo.setText("1234");
        componentInfo.setTitle("");
        componentInfo.setToolTipText("Enter the Accounting Template(right mouse or Shift-F10 for list)");
//        componentInfo.setToolTipText("Enter the Organization(right mouse or Shift-F10 for list)");
        //componentInfo.setxCoordinate(78);
        //componentInfo.setyCoordinate(18);
        componentInfo.setRequestFocus(false);
        componentInfo.setiValue(0);
        ac.getComponents().add(componentInfo);
        String message = xstream.toXML(ac);
        ac = client.execute(MAIN_URL, ac);
        assertNotNull(ac);
    }
}
