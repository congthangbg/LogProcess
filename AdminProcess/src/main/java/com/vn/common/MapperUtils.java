package com.vn.common;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MapperUtils {
private static ModelMapper mapper = new ModelMapper();
	
	static {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	private MapperUtils() {
		
	}
	
	/**
	 * @param <D> 		type of result object
	 * @param <T> 		type of source object to map from
	 * @param entity	that needs to be mapped
	 * @param outClass  class of result object
	 * @return new object of <code>outClass</code> type 
	 * */
	public static <D,T> D map(final T entity,Class<D> outClass) {
    	return mapper.map(entity, outClass);
    }
    
    public static <D,T> List<D> mapAll(final Collection<T> entityList,Class<D> outClass)
    {
        return entityList.stream()
        		.map(entity -> map(entity, outClass))
        		.collect(Collectors.toList());
    }
    
    /**
     * @param source  		object to map from
     * @param destination   object to map to
     * */
    public static <S,D> D map(final S source,D destination) {
    	mapper.map(source, destination);
    	return destination;
    }
}
