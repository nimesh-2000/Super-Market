package bo.custom;

import bo.SuperBO;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IncomeBO extends SuperBO {
    ArrayList<CustomDTO> income() throws SQLException, ClassNotFoundException;
}
