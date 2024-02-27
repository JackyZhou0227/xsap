package com.kclm.xsap.dto.convert;

import com.kclm.xsap.entity.MemberCardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.kclm.xsap.dto.ConsumeRecordDTO;
import com.kclm.xsap.entity.ConsumeRecordEntity;

/**
 * 
 * @author harima
 * @since JDK11.0
 * @CreateDate 2020年9月18日 下午5:32:58 
 * @description 此类用来描述了消费记录DTO类型转换
 *
 */
@Mapper(componentModel = "spring")
public interface ConsumeRecordConvert {

	//ConsumeRecordConvert INSTANCE = Mappers.getMapper(ConsumeRecordConvert.class);
	/**
	 * 
	 * @param consume	对应消费记录实体类
	 * @param card	对应会员卡实体类
	 * @return	ConsumeRecordDTO。消费记录要展示的信息
	 */
	@Mappings({
		@Mapping(source = "consume.id", target = "consumeId"),//??没有id?
//		@Mapping(source = "card.id", target = "cardId"),
		@Mapping(source = "card.name",target = "cardName"),
		@Mapping(source = "consume.createTime",target = "operateTime"),
		//以消费记录的note为映射依据
		@Mapping(source = "consume.note",target = "note")
	})
	ConsumeRecordDTO entity2Dto(ConsumeRecordEntity consume, MemberCardEntity card, Integer timesRemainder);
	
	
	
}
