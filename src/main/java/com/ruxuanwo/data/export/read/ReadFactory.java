package com.ruxuanwo.data.export.read;

import com.ruxuanwo.data.export.read.impl.ReadFromJar;

/**
 * @author ruxuanwo
 * 2019-6-25 9:17
 */
public class ReadFactory {

    public static AbstractRead getRead(){
        return getRead(ReadEnum.FROM_JAR.getMark());
    }

    public static AbstractRead getRead(String mark){
        return ReadEnum.getReadFromMark(mark);
    }

    private enum ReadEnum{

        /**
         *
         */
        FROM_JAR("fromJar", new ReadFromJar());

        private String mark;

        private AbstractRead read;

        ReadEnum(String mark, AbstractRead read) {
            this.mark = mark;
            this.read = read;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public AbstractRead getRead() {
            return read;
        }

        public void setRead(AbstractRead read) {
            this.read = read;
        }

        public static AbstractRead getReadFromMark(String mark){
            for (ReadEnum readEnum : ReadEnum.values()) {
                if (readEnum.getMark().equals(mark)){
                    return readEnum.getRead();
                }
            }
            return null;
        }
    }

}
