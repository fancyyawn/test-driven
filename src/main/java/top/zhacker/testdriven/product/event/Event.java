package top.zhacker.testdriven.product.event;

import com.google.common.eventbus.EventBus;


/**
 * Created by zhacker.
 * Time 2017/5/28 上午10:48
 * Desc 文件描述
 */
public class Event {
   
   protected final EventBus eventBus;
   
   public Event(EventBus eventBus) {
      this.eventBus = eventBus;
   }
   
   public void post(){
       eventBus.post(this);
   }
}
