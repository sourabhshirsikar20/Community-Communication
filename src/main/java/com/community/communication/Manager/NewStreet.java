package com.community.communication.Manager;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewStreet {
  @NotEmpty(message="street must have an address")
  @Size(max=126, message="only up to 63 nested streets are supported")
  String address;
  
  @NotEmpty(message="street must have a name")
  @Size(max=64, message="street name too large")
  String name;

  @Size(max=512, message="street description too large")
  String description;
}
