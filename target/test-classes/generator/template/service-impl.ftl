package ${basePackage}.service.${dbtype}.impl;

import ${basePackage}.dao.${dbtype}.${modelNameUpperCamel}Mapper;
import ${basePackage}.model.${dbtype}.${modelNameUpperCamel};
import ${basePackage}.service.${dbtype}.${modelNameUpperCamel}Service;
import ${basePackage}.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
