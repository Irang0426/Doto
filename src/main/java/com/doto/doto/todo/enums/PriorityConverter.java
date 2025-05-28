package com.doto.doto.todo.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PriorityConverter implements AttributeConverter<Priority, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Priority attribute) {
    if (attribute == null) return null;
    return attribute.getValue();
  }

  @Override
  public Priority convertToEntityAttribute(Integer dbData) {
    if (dbData == null) return null;
    for (Priority priority : Priority.values()) {
      if (priority.getValue() == dbData) {
        return priority;
      }
    }
    throw new IllegalArgumentException("Unknown database value: " + dbData);
  }
}
