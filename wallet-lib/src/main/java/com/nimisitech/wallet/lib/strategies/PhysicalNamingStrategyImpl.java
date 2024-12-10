package com.nimisitech.wallet.lib.strategies;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

public class PhysicalNamingStrategyImpl extends PhysicalNamingStrategyStandardImpl {

    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
    }

    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
    }

    protected static String addUnderscores(String name) {
        StringBuilder builder = new StringBuilder(name.replace('.', '_'));

        for(int i = 1; i < builder.length() - 1; ++i) {
            if (Character.isLowerCase(builder.charAt(i - 1)) && Character.isUpperCase(builder.charAt(i)) &&
                    Character.isLowerCase(builder.charAt(i + 1))) {
                builder.insert(i++, '_');
            }
        }

        return builder.toString().toLowerCase(Locale.ROOT);
    }
}
