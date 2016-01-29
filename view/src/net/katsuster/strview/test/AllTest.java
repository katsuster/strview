package net.katsuster.strview.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.katsuster.strview.test.util.*;

/**
 * ユニットテストクラス。
 */
@RunWith(Suite.class)
@SuiteClasses({
        SIntTest.class,
        UIntTest.class,
})
public class AllTest {
    protected AllTest() {
        //do nothing
    }

    public static void main(String[] args) {
        JUnitCore.main(AllTest.class.getName());
    }
}
