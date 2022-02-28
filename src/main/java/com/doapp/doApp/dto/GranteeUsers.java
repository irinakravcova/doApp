package com.doapp.doApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@ToString
public class GranteeUsers {
    List<UserDTO> users = new ArrayList<>();
}
