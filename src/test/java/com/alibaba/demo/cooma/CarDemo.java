/*
 * Copyright 2012-2013 Cooma Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.demo.cooma;

import com.alibaba.cooma.ExtensionLoader;
import com.alibaba.demo.cooma.car.Car;
import com.alibaba.demo.cooma.wheel.Wheel;
import com.alibaba.util.Utils;

import java.util.Arrays;

/**
 * @author Jerry Lee(oldratlee AT gmail DOT com)
 */
public class CarDemo {
    public static void main(String[] args) {
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);

        // 演示 扩展的获得、关联扩展点的注入（Car扩展点引用了Wheel）

        Car defaultCar = extensionLoader.getDefaultExtension();
        defaultCar.run();

        System.out.println("=================================");

        Car car1 = extensionLoader.getExtension("racing");
        car1.run();

        System.out.println("=================================");

        //这个方法应该会根据参数的类型进行匹配，规则应该是把第二个开始的参数代表的类型注入到第一个参数的对应方法中
        Car car2 = extensionLoader.getExtension("sport", Utils.kv2Map(Wheel.class.getName(), "wood"));
        car2.run(); // 缺省使用RubberWheel

        System.out.println("=================================");

        Car car3 = extensionLoader.getExtension(Utils.kv2Map(Car.class.getName(), "racing", Wheel.class.getName(), "wood"));
        car3.run(); // 缺省使用RubberWheel

        // 演示 Wrapper的使用

        System.out.println("=================================");

        //Map类型的为扩展点，List类型为wrapper
        Car countedSportCar1 = extensionLoader.getExtension("sport", Arrays.asList("run_counter"));
        Car countedSportCar2 = extensionLoader.getExtension("sport", Arrays.asList("run_counter"));

        countedSportCar1.run();
        countedSportCar2.run();
        countedSportCar1.run();
    }
}
