package com.api.data;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][] {
                {"John Doe", "Engineer"},
                {"Sakthi", "Tester"}
        };
    }
}
