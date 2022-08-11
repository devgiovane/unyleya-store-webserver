package com.github.giovane.webserver.dto;

import javax.validation.constraints.NotNull;

public record ProductDto(@NotNull String name, @NotNull Double price) { }
