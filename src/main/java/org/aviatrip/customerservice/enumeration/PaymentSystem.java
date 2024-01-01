package org.aviatrip.customerservice.enumeration;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public enum PaymentSystem implements Formattable {
    QIWI("Qiwi"),
    SBERBANK("Sberbank"),
    PAYPAL("Paypal"),
    WEB_MONEY("Web money");

    private final String name;
    private static final Map<String, PaymentSystem> map = new HashMap<>();

    static {
        for(PaymentSystem system : PaymentSystem.values()) {
            map.put(system.name, system);
        }
    }

    @Override
    public String getFormattedName() {
        return name;
    }

    public static PaymentSystem of(String name) {
        return Optional.ofNullable(map.get(name))
                .orElseThrow(() -> new NoSuchElementException("no such payment system"));
    }
}
