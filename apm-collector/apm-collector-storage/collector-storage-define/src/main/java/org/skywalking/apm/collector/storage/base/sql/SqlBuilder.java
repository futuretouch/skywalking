/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.collector.storage.base.sql;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * SQL 生成器
 */
public class SqlBuilder {

    /**
     * 生成基于占位符的 SQL
     *
     * @param sql SQL
     * @param args 参数数组
     * @return SQL
     */
    public static String buildSql(String sql, Object... args) {
        return MessageFormat.format(sql, args);
    }

    /**
     * 生成基于占位符的 SQL
     *
     * @param sql SQL
     * @param args 参数数组
     * @return SQL
     */
    public static String buildSql(String sql, List<Object> args) {
        MessageFormat messageFormat = new MessageFormat(sql);
        return messageFormat.format(args.toArray(new Object[0]));
    }

    /**
     * 生成批量插入 SQL
     *
     * @param tableName 表名
     * @param columnNames 字段数组
     * @return SQL
     */
    public static String buildBatchInsertSql(String tableName, Set<String> columnNames) {
        StringBuilder sb = new StringBuilder("insert into ");
        sb.append(tableName).append("(");
        columnNames.forEach((columnName) -> sb.append(columnName).append(","));
        sb.delete(sb.length() - 1, sb.length());
        sb.append(") values(");
        for (int i = 0; i < columnNames.size(); i++) {
            sb.append("?,");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(")");
        return sb.toString();
    }

    /**
     * 生成批量更新 SQL
     *
     * @param tableName 表名
     * @param columnNames 字段数组
     * @return SQL
     */
    public static String buildBatchUpdateSql(String tableName, Set<String> columnNames, String whereClauseName) {
        StringBuilder sb = new StringBuilder("update ");
        sb.append(tableName).append(" set ");
        columnNames.forEach((columnName) -> sb.append(columnName).append("=?,"));
        sb.delete(sb.length() - 1, sb.length());
        sb.append(" where ").append(whereClauseName).append("=?");
        return sb.toString();
    }

}
