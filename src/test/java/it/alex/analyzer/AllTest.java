package it.alex.analyzer;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Ignore
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TryTest.class,
        FalseTest.class,
        ExceptionTest.class})
public class AllTest {
}
