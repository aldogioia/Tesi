package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class AccessDto {
    private String accessRole;
    private Boolean firstAccess;
}
