package com.educaciontacna.drednot.ui.helpers;

/**
 * Created by rogergcc on 22/02/2021.
 * Copyright Ⓒ 2021 . All rights reserved.
 */
public enum EstadoDocumento {
    PENDIENTE("Pendiente"),
    NOTIFICADO("Notificado"),
    INACTIVO("inactivo"),
    UNKNOWN("unknown");


    private String value;

    // endregion

    // ---------------------------------------------------------------------------------------------
    // region Initialization

    EstadoDocumento(String value) {
        this.value = value;
    }

    // endregion

    // ---------------------------------------------------------------------------------------------
    // region Override Methods


    @Override
    public String toString() {
        return this.getValue();
    }

    // endregion

    // ---------------------------------------------------------------------------------------------
    // region Methods

    public String getValue() {
        return value;
    }

    /**
     * getEnumFor(String value)
     * - Use this custom method instead of build in 'EstadoDocumento.valueOf()' b/c it works for custom enum values.
     * @param value - String that corresponds to enum value.
     * @return - EstadoDocumento
     */
    public static EstadoDocumento getEnumFor(String value) {
        for(EstadoDocumento customEnum : EstadoDocumento.values()) {
            if (customEnum.getValue().equalsIgnoreCase(value)) {
                return customEnum;
            }
        }
        return EstadoDocumento.UNKNOWN;
    }

    // endregion
}
