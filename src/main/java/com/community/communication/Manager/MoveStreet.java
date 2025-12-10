package com.community.communication.Manager;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveStreet {
  @NotEmpty(message="Address cannot be empty")
  @Size(max=126, message="only up to 63 nested streets are supported")
  String currentAddress, newAddress;
}
