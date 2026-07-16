# Notification Id Rule

## Total Id type list

1. from 100 to 1000
2. from 1000 to 10000
3. Above 10000
----
### from 100 to 1000
作为保留字段进行后续规划, 比如：

    100( Promoted Notificaiton - Basic, Only Content , Not Live Update ) -> 1000( index leader ), 1001,1002...1999
    200( Promoted Notificaiton - Basic, Title And Content , Not Live Update ) -> 2000( index leader ), 2001,2002...2999

总而言之，100..999是为 **Promoted Notification** 也就是最基础的 流体云/灵动岛 通知服务的 **保留频段**

### from 1000 to 9999
作为ID字段，按照上方的语义索引, 比如：

    1401( Promoted Notificaiton - Basic, Only Content , Not Live Update )
    2309( Promoted Notificaiton - Basic, Title And Content , Not Live Update )

每一千的开始，个位的0为保留数据，提供给 Data Class 的测试语义索引。不可入库，这是 <span style="color: red;">软性要求</span>
比如：

    1000( Promoted Notificaiton - Basic, Only Content , Not Live Update , ONLY_FOR_TEST_DEMO )
    2000( Promoted Notificaiton - Basic, Title And Content , Not Live Update , ONLY_FOR_TEST_DEMO )

总而言之，1000..9999是为 **Promoted Notification** 也就是最基础的 流体云/灵动岛 通知服务的 **语义索引**

----
### Above 10000
作为 Power Cloud 字段进行后续规划
