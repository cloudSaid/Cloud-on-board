package com.mashibing.serviceprice.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cpf
 * @since 2022-10-11
 */
@Service
public class PriceRuleService {

    @Autowired
    PriceRuleMapper priceRuleMapper;

    /*
    * 添加计价规则
    * */
    public ResponseResult add(PriceRule priceRule) {

        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        //拼接FareType
        priceRule.setFareType(cityCode + "$" + vehicleType);

        //添加版本号
        Map<String, Object> map = new HashMap<>();
        map.put("city_code",cityCode);
        map.put("vehicle_type",vehicleType);

        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);

        int fareVersion = 0;
        if (priceRules.size() > 0){
            fareVersion = priceRules.get(0).getFareVersion();
        }

        priceRule.setFareVersion(fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("");
    }

    public ResponseResult<Boolean> ifExistence(PriceRule priceRule) {
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        List<PriceRule> priceRules = priceRuleMapper.selectList(new QueryWrapper<PriceRule>()
                .eq("city_code", cityCode)
                .eq("vehicle_type", vehicleType)
        );

        if (priceRules.size() > 0){
            return ResponseResult.success(true);
        }else {
            return ResponseResult.success(false);
        }
    }
}
