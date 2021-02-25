package com.educaciontacna.drednot.ui.helpers;

/**
 * Created by rogergcc on 22/02/2021.
 * Copyright Ⓒ 2021 . All rights reserved.
 */
public enum EstadoDocumentoEnum {
    PENDIENTE("Pendiente"),
    NOTIFICADO("Notificado"),
    INACTIVO("inactivo"),
    UNKNOWN("unknown");


    private String value;

    // endregion

    // ---------------------------------------------------------------------------------------------
    // region Initialization

    EstadoDocumentoEnum(String value) {
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
     * - Use this custom method instead of build in 'EstadoDocumentoEnum.valueOf()' b/c it works for custom enum values.
     * @param value - String that corresponds to enum value.
     * @return - EstadoDocumentoEnum
     */
    public static EstadoDocumentoEnum getEnumFor(String value) {
        for(EstadoDocumentoEnum customEnum : EstadoDocumentoEnum.values()) {
            if (customEnum.getValue().equalsIgnoreCase(value)) {
                return customEnum;
            }
        }
        return EstadoDocumentoEnum.UNKNOWN;
    }

    // endregion
}
