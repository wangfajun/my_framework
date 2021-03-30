package com.wangfajun.framework.aes;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FrameWorkEncryptHandler extends BaseTypeHandler {

    @Autowired
    private FrameWorkAESCipher salaryAesCipher;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, salaryAesCipher.encrypt((String)parameter));
    }
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return salaryAesCipher.decrypt(columnValue);
    }
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return salaryAesCipher.decrypt(columnValue);
    }
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return salaryAesCipher.decrypt(columnValue);
    }
}
