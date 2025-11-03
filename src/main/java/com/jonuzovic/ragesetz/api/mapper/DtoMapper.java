package com.jonuzovic.ragesetz.api.mapper;

public interface DtoMapper<Entity, DataTransferObject> {
	DataTransferObject mapToDto(Entity entity);
}