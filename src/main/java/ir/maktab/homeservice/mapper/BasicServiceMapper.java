package ir.maktab.homeservice.mapper;

import ir.maktab.homeservice.dto.BasicServiceDto;
import ir.maktab.homeservice.entity.BasicService;
import org.mapstruct.factory.Mappers;

public interface BasicServiceMapper {

    BasicServiceMapper INSTANCE = Mappers.getMapper(BasicServiceMapper.class);

    BasicServiceDto entityToDTO(BasicService source);

    BasicService DTOtoEntity(BasicServiceDto destination);

}