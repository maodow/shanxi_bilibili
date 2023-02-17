package tv.huan.bilibili.bean;

public interface JumpBean {

    int getToType();

    int getClassId();

    String getCid();

    String getName();

    default int getSpecialType() {
        return 2;
    }

    default int getSpecialId() {
        try {
            return Integer.parseInt(getCid());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    default String getFilterSecondTag() {
        return getName();
    }

    default int getFilterId() {
        return getClassId();
    }
}
