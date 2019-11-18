package com.hugh.basis;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by chenyw on 2019-09-29.
 */
//@DefaultLifeCycle(
//        // 将 要自动生成的application的类，指定生成位置和类名
//        application = "com.hugh.basis.HughApplication ",
//        flags = ShareConstants.TINKER_ENABLE_ALL,
//        //loaderClassName, 我们这里使用默认即可!
//        loaderClass = "com.tencent.tinker.loader.TinkerLoader",
//        loadVerifyFlag = false)
public class HughApplicationLike extends ApplicationLike {


    public HughApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // 分包需要的东西
//        MultiDex.install(base);

        // LoadReporter类定义了Tinker在加载补丁时的一些回调
        LoadReporter loadReporter = new DefaultLoadReporter(getApplication());
        // PatchReporter类定义了Tinker在修复或者升级补丁时的一些回调
        PatchReporter patchReporter = new DefaultPatchReporter(getApplication());
        // PatchListener类是用来过滤Tinker收到的补丁包的修复、升级请求，也就是决定我们是不是真的要唤起:patch进程去尝试补丁合成。
        PatchListener patchListener = new DefaultPatchListener(getApplication());
        // UpgradePatch类是用来升级当前补丁包的处理类，一般来说你也不需要复写它。
        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(this,
                loadReporter, patchReporter, patchListener,
                DefaultTinkerResultService.class, upgradePatchProcessor);

    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
