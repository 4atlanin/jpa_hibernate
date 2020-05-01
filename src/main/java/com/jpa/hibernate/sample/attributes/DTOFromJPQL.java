package com.jpa.hibernate.sample.attributes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOFromJPQL
{
    private String dtoPayload;
    private String dtoAnotherString;
}
