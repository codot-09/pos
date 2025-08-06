package com.example.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebtRequest {
    private String debtorName;
    private String contactInfos;
}
