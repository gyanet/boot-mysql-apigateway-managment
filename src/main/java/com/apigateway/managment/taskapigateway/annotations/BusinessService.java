package com.apigateway.managment.taskapigateway.annotations;

import org.springframework.stereotype.Service;
import javax.transaction.SystemException;
import java.lang.annotation.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = {SystemException.class})
public @interface BusinessService {
}
