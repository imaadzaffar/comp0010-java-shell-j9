package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import uk.ac.ucl.shell.applications.*;
import uk.ac.ucl.shell.exceptions.UnknownApplicationException;

public class ApplicationFactoryTest {
    ApplicationFactory applicationFactory;

    public ApplicationFactoryTest() {
        applicationFactory = new ApplicationFactory();
    }

    @Test
    public void testUnsafe() {
        String appName = "_echo";
        Application app = applicationFactory.getApp(appName);
        assertEquals(UnsafeDecorator.class, app.getClass());
    }

    @Test
    public void testCd() {
        String appName = "cd";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Cd.class, app.getClass());
    }

    @Test
    public void testPwd() {
        String appName = "pwd";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Pwd.class, app.getClass());
    }

    @Test
    public void testLs() {
        String appName = "ls";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Ls.class, app.getClass());
    }

    @Test
    public void testCat() {
        String appName = "cat";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Cat.class, app.getClass());
    }

    @Test
    public void testEcho() {
        String appName = "echo";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Echo.class, app.getClass());
    }

    @Test
    public void testHead() {
        String appName = "head";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Head.class, app.getClass());
    }

    @Test
    public void testTail() {
        String appName = "tail";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Tail.class, app.getClass());
    }

    @Test
    public void testGrep() {
        String appName = "grep";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Grep.class, app.getClass());
    }

    @Test
    public void testFind() {
        String appName = "find";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Find.class, app.getClass());
    }

    @Test
    public void testSort() {
        String appName = "sort";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Sort.class, app.getClass());
    }

    @Test
    public void testUniq() {
        String appName = "uniq";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Uniq.class, app.getClass());
    }

    @Test
    public void testCut() {
        String appName = "cut";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Cut.class, app.getClass());
    }

    @Test
    public void testRm() {
        String appName = "rm";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Rm.class, app.getClass());
    }

    @Test
    public void testMkdir() {
        String appName = "mkdir";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Mkdir.class, app.getClass());
    }

    @Test
    public void testTouch() {
        String appName = "touch";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Touch.class, app.getClass());
    }

    @Test
    public void testSleep() {
        String appName = "sleep";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Sleep.class, app.getClass());
    }

    @Test
    public void testDate() {
        String appName = "date";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Date.class, app.getClass());
    }

    @Test
    public void testWc() {
        String appName = "wc";
        Application app = applicationFactory.getApp(appName);
        assertEquals(Wc.class, app.getClass());
    }

    @Test(expected = UnknownApplicationException.class)
    public void testUnknownApplication() {
        String appName = "Eren";
        applicationFactory.getApp(appName);
    }
}
