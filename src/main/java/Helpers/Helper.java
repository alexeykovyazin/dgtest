package Helpers;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static Constants.InitData.*;

import static org.openqa.selenium.io.FileHandler.delete;

public class Helper {

    private WebDriver _driver;
    private WebElement _current;

    /**  ------------ The new age helper ---------------- 
     * 
     *  (new Helper(driver))
     *      .current( myElement).setValue( value )
     *      .current( form ).click()
     *      .waitUpdate()
     *      ;
    */

    public Helper(WebDriver driver) {
        _driver = driver;
    }

    public Helper current(WebElement element) {
        _current = element;
        return this;
    }

    public Helper clear() {
        clear(_current, _driver);
        return this;
    }

    /** DoubleClick onthe element and send text. On dobleclick current element can be changed by js */
    public Helper doubleClickAndSetText(String value) {
        final Actions navigator = new Actions(_driver);
        navigator.doubleClick(_current).sendKeys(value).perform();
        return this;
    }


    public Helper setValue(String value) {
        //clear();
        _current.clear();
        _current.sendKeys(value);
        return this;
    }

    public Helper tab() {
        _current.sendKeys(Keys.TAB);
        return this;
    }

    public String getColor(String cssvalue) {
        String color = _current.getCssValue(cssvalue);
        String hex = Color.fromString(color).asHex();
        return hex;
    }

    public String getParam(String ur1, String param) throws MalformedURLException, UnsupportedEncodingException {
        return String.valueOf(getParamsUrl(new URL(ur1)).get(param)).replaceAll("\\[|\\]", "");
    }

    public int getLocationX() { return _current.getLocation().getX();  }
    public int getLocationY() {
        return  _current.getLocation().getY();
    }

    public Helper moveToElementAndClick() {
        final Actions navigator = new Actions(_driver);
        navigator.moveToElement(_current).click().build().perform();
        return this;
    }

    public Helper scrollToElement(){
        //final Actions builder = new Actions(_driver);
        ((JavascriptExecutor)_driver).executeScript("arguments[0].scrollIntoView(true);", _current);
        return  this;
    }
    public Helper pushStateUrl(String url){
        ((JavascriptExecutor)_driver).executeScript("window.history.pushState(null,\"\",'"+url+"');");
        return  this;
    }

    public String sidebarRouteMap(String aspectMenu){
        Object aspect = ((JavascriptExecutor)_driver).executeScript("return window.sidebarRouteMap('"+aspectMenu+"');");
        return String.valueOf(aspect);
    }

    public Helper mouseScrolling(int x, int y){
        final Actions builder = new Actions(_driver);
        builder.clickAndHold(_current).moveByOffset(x,y).pause(1000).moveByOffset(x,y).release().build().perform();
        return  this;
    }
    public Helper moveto() {
        final Actions navigator = new Actions(_driver);
        navigator.moveToElement(_current).perform();
        return this;
    }

    public Helper pauseAction(int time) {
        final Actions navigator = new Actions(_driver);
        navigator.pause(time).perform();
        return this;
    }

    public Helper clickHold() {
        final Actions navigator = new Actions(_driver);
        navigator.clickAndHold(_current).perform();
        return this;
    }
    public Helper moveToAndReleaseElement() {
        final Actions navigator = new Actions(_driver);
        navigator.moveToElement(_current).release().perform();
        return this;
    }

    public Helper dragAndDropElements(WebElement drag, WebElement drop) {
        final Actions builder = new Actions(_driver);
        builder.dragAndDrop(drag,drop).perform();
        return this;
    }

    public Helper dragAndDropByCoordinate(int x, int y) throws InterruptedException {
        final Actions builder = new Actions(_driver);
        builder.moveToElement(_current).clickAndHold(_current).moveByOffset(x,y).pause(1000).moveByOffset(x,y).release(_current).build().perform();
        return this;
    }

    public Helper dragAndDropByCoordinateOneTuch(int x, int y) throws InterruptedException {
        final Actions builder = new Actions(_driver);
        builder.moveToElement(_current).clickAndHold(_current).moveByOffset(x,y).release(_current).build().perform();
        return this;
    }
    public boolean moveToElementBoolean() {
        final Actions navigator = new Actions(_driver);
        try {
            navigator.moveToElement(_current).build().perform();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }


    public Helper keyEnter() {
        Actions builder = new Actions(_driver);
        builder.sendKeys(_current, Keys.RETURN).build().perform();
        //_current.sendKeys(Keys.ENTER);
        return this;
    }
    public Helper mouseAct() {
        Actions builder = new Actions(_driver);
        builder.sendKeys(_current, Keys.RETURN).build().perform();
        //_current.sendKeys(Keys.ENTER);
        return this;
    }

    public Helper doubleClick() {
        Actions navigator = new Actions(_driver);
        navigator.doubleClick(_current).perform();
        return this;
    }

    public Helper click() {
        Actions navigator = new Actions(_driver);
        navigator.click(_current).perform();
        return this;
    }

    public Helper RightClick() {
        Actions actions = new Actions(_driver);
        actions.contextClick(_current).perform();
        return this;
    }

    public Helper waitUpdate() {
        waitUpdate(_driver);
        return this;
    }

    public String text() {
        return _current.getAttribute("textContent");
    }
    public String getAtributteStyleTop() {

        String result = _current.getAttribute("style");
        if (result == null) {
            return null;
        }else{
            String resultstart = result.replaceAll("(.*top: )", "");
            String resultend = resultstart.replaceAll("(px.*)", "");
            return resultend;
        }

    }

    public String attr(String attributeName) {
        String result = _current.getAttribute(attributeName);
        if (result == null) {
            return null;
        }
        return result.trim();
    }

    public boolean hasClass(String string) {
        String attr = attr("class");
        if (attr == null) {
            return false;
        }
        return attr.contains(string);
    }

    public void openPortlet() {
        if (hasClass("expand")) {
            click();
        }
    }

    public Helper hoverAndClick(WebElement clikableElement) {

        Actions builder = new Actions(_driver);
        builder.moveToElement(_current).click(clikableElement);
        Action mouseoverAndClick = builder.build();
        mouseoverAndClick.perform();
        return this;
    }
    
    /** ------------------------------------------------------------- */


    public static WebElement setValue(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
        element.sendKeys(Keys.TAB);
        return element;
    }

    public static void isHoverElement(WebElement elementhover, WebElement elementclick, WebDriver driver) {

        Actions builder = new Actions(driver);
        builder.moveToElement(elementhover).click(elementclick);
        Action mouseoverAndClick = builder.build();
        mouseoverAndClick.perform();
    }

    public Object runScript(String script) {
        // Helper.log("Run Script:" + script);
        Object result = ((JavascriptExecutor) _driver).executeScript(script);
        if (result != null) {
            //log("Run Script:" + script);
            //log("Script result: " + result.toString());
        } else {
            //log("Script result: undefined");
        }

        return result;
    }

    /**
     *
     * The method waits for the completion of the request. Additionally, an explicit wait for an item 1 sec
     */
    public static void waitUpdate(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until((ExpectedCondition<Boolean>) wdriver -> ((JavascriptExecutor) driver)
                    .executeScript("return((window.jQuery != null) && (jQuery.active === 0))").equals(true));
        } catch (WebDriverException exWebDriverException) {
            Assert.fail("Timeout driver. Not enough time to display the item.");
        }
    }

    public static void waitSetup(WebDriver driver, int time) {
        try {
            Thread.sleep(time);
        } catch (WebDriverException | InterruptedException exWebDriverException) {
            Assert.fail("Timeout driver. Not enough time to display the item.");
        }
    }

    /**
     *
     * The method checks the presence of an element on the page
     */
//    public static boolean isDisplayedElement(WebElement element) {
//        try {
//            return element.isDisplayed();
//        } catch (NoSuchElementException ex) {
//            return false;
//        }
//    }

    public boolean displayedElement() {
        try {
            _current.isDisplayed();
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
    public boolean isdisplayedElement(WebElement element) {
        try {
            _driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     *  the method implicit expectations
     */
    public  boolean implicitlyWaitElement() {
        try {
            _driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            return true;
        } catch (TimeoutException ex) {
            return false;
        }

    }
    /**
     *
     * the method waits for the element to appear
     * @return
     */
    public boolean waitelementToBeClickable() {
            try {
            WebDriverWait wait = new WebDriverWait(_driver, 10);
            //clickable
            wait.until(ExpectedConditions.elementToBeClickable(_current));
                return false;
        } catch (TimeoutException ex) {
        }
        return false;
    }
    /**
     *
     * the method to get milliseconds from the date
     */
    public long getMilliFromDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    /**
     * Replaces {@link WebElement#clear()} in order to simulate what a real user
     * would do to clear an input text field, by not calling onchange JavaScript
     * event, and by calling oninput and keystroke events (Ctrl+Home,
     * Ctrl+Shift+End, BackSpace).
     * <p>
     * {@link WebElement#clear()} is buggy because it fires onfocus (if not already
     * focused) and then onchange! The onchange event must be fired only after the
     * input value has changed AND it has lost focus. When clearing, the input do
     * not lose focus, so Selenium is buggy here. It must trigger oninput instead
     * (and it doesn't). Here is the initial issue closed by mistake without fix:
     * https://github.com/SeleniumHQ/selenium-google-code-issue-archive/issues/214
     * and here is the new issue I created to solve that for good:
     * https://github.com/SeleniumHQ/selenium/issues/1841
     *
     * @param element a text INPUT or TEXTAREA field
     */
    public static void clear(WebElement element, WebDriver driver) {
        Actions navigator = new Actions(driver);
        if (element.getTagName() == "TEXTAREA") {
            navigator.click(element) //
                    .keyDown(Keys.CONTROL) //
                    /* */.sendKeys(Keys.HOME) //
                    /* */.keyDown(Keys.SHIFT) //
                    /* * * */.sendKeys(Keys.END) //
                    /* */.keyUp(Keys.SHIFT) //
                    .keyUp(Keys.CONTROL) //
                    .sendKeys(Keys.BACK_SPACE).perform();
        }
        else {
            navigator.click(element) //
                    .sendKeys(Keys.HOME) //
                    /* */.keyDown(Keys.SHIFT) //
                    /*     */.sendKeys(Keys.END) //
                    /* */.keyUp(Keys.SHIFT) //
                    .sendKeys(Keys.BACK_SPACE).perform();
        }

    }

    public static void clearTextBox(WebElement element, WebDriver driver) {
        Actions navigator = new Actions(driver);
        navigator.click(element) //
                .sendKeys(Keys.HOME) //
                /* */.keyDown(Keys.SHIFT) //
                /*     */.sendKeys(Keys.END) //
                /* */.keyUp(Keys.SHIFT) //
                .sendKeys(Keys.BACK_SPACE).perform();
    }


    public static void log(String msg)
    {
        System.out.println(msg);
    }

    public static void waitKey() {
        Scanner console = new Scanner(System.in);
        log("Press enter to continue");
        console.nextLine();
	}

    public static void waitKey(String prompt) {
        Scanner console = new Scanner(System.in);
        log(prompt);
        log("Press enter to continue");
        console.nextLine();
    }

    /**
     * Function Parse URL
     */
    public static Map<String, List<String>> getParamsUrl(java.net.URL url) throws UnsupportedEncodingException {
        //System.out.println(url);
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }

    /**
     * Intercept function JS ERROR
     */
    public static String interceptionJSonPage(WebDriver webDriver) {
        Set<String> errorStrings = new HashSet<>();
        errorStrings.add("SyntaxError");
        errorStrings.add("EvalError");
        errorStrings.add("ReferenceError");
        errorStrings.add("RangeError");
        errorStrings.add("TypeError");
        errorStrings.add("URIError");
        //errorStrings.add("404");

        LogEntries logEntries = webDriver.manage().logs().get(LogType.BROWSER);
        if (logEntries != null) {
            for (LogEntry logEntry : logEntries) {
                for (String errorString : errorStrings) {
                    if (logEntry.getMessage().contains(errorString)) {
                        Assert.fail(new Date(logEntry.getTimestamp()) + " " + logEntry.getLevel() + " " + logEntry.getMessage());
                    }
                }
            }
        }
        return null;
    }


    /**
     * Function Deleting Folder
     */
    // method search expansion file and delete him
    public static void findExpFilesRenameCopy(String dir, String ext) {
        File file = new File(dir);
        if(!file.exists()) System.out.println(dir + " dir not found");
        File[] listFiles = file.listFiles(new MyFileNameFilter(ext));
        if(listFiles.length == 0){
            System.out.println(dir + " does not contain files with the extension  " + ext);
        }else{
            for(File f : listFiles)
               file = new File(dir + File.separator + f.getName());
            try {
                copyDirectory(file,new File(Replica_Async_DB_Path));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // method search expansion file and delete him
    public static void findExpFilesDelete(String dir, String ext) {
        File file = new File(dir);
        if(!file.exists()) System.out.println(dir + " dir not found");
        File[] listFiles = file.listFiles(new MyFileNameFilter(ext));
        if(listFiles.length == 0){
            System.out.println(dir + " does not contain files with the extension " + ext);
        }else{
            for(File f : listFiles)
                delete(new File(dir + File.separator + f.getName()));
                System.out.println("Delete file"  );
        }
    }

    // Interface FileNameFilter
    public static class MyFileNameFilter implements FilenameFilter{

        private String ext;

        public MyFileNameFilter(String ext){
            this.ext = ext.toLowerCase();
        }
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }

    public void deleteFolderOrFile(final File file) {
        System.out.println("Delete file: " + file.getAbsolutePath());
        if(file.isDirectory()) {
            String[] files = file.list();
            if((null == files) || (files.length == 0)) {
                file.delete();
            } else {
                for(final String filename: files) {
                    delete(new File(file.getAbsolutePath() + File.separator + filename));
                }
                file.delete();
            }
        } else {
            file.delete();
        }
    }
    public static void copyDirectory(File src, File dst) throws IOException {
        try {

            if (src.isDirectory()) {

                // If there is no directory, then you need to create it ("copy" the name of the directory)
                if (!dst.exists()) {
                    dst.mkdir();
                    //System.out.println("Directory copied from " + src + "  in" + dst);
                }
                // We determine the array by the contents of the source directory
                String files[] = src.list();

                for (String file : files) {
                    // create the structure of the destination directory by analogy with the source directory
                    File srcFile = new File(src, file);
                    File dstFile = new File(dst, file);
                    // recursive copying
                    copyDirectory(srcFile, dstFile);
                }
            } else {
                // if the object passed to the procedure, then it is copied through the streams byte by byte

                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dst);

                byte[] buffer = new byte[1024];

                int length;
                //copy file contents according to buffer size
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();

                //System.out.println("File copied from " + src + " in " + dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        exists(fileName);

        try {
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }


    public static void existFileExt(String dir, String ext) {
        File file = new File(dir);
        if(!file.exists()) System.out.println(dir + " dir not found");
        File[] listFiles = file.listFiles(new MyFileNameFilter(ext));
        if(listFiles.length == 0){

        }else{
            Assert.fail(""+dir+"+\"file extension\"+"+ext+"+\" not found\"");
        }
    }
}
