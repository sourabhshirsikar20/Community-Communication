package com.community.communication.Approved;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StreetInfo {
  Street parent, self;
  List<Street> children;
}
