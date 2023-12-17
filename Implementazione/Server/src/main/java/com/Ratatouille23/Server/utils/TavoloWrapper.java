package com.Ratatouille23.Server.utils;

import com.Ratatouille23.Server.entity.Tavolo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TavoloWrapper {

    private List<DtoElementi> dtoElementi;

    private Tavolo tavolo;

}
