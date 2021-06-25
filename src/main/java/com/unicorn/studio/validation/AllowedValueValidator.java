package com.unicorn.studio.validation;

import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowedValueValidator implements ConstraintValidator<AllowedValue, String> {
    private enum OrgType { PRIVATE, PUBLIC, STARTUP, NON_PROFIT; }
    private enum Role { COMPANY, INVESTOR, ACCELERATOR, INCUBATOR}
    private enum RegionCodes { NA,SA,EU,ME,AF,OC,AS,AN; }

    private Class<? extends Enum> context;

    @Override
    public void initialize(AllowedValue allowedValue) {
        switch (allowedValue.type()) {
            case "Org":
                context = OrgType.class;
            case "Role":
                context = Role.class;
            case "Region":
                context = RegionCodes.class;
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && EnumUtils.isValidEnum(context, value);
    }
}
