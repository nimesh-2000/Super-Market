package bo.custom.impl;

import bo.custom.IncomeBO;
import dao.DAOFactory;
import dao.custom.QueryDAO;
import dto.CustomDTO;
import entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeBOImpl implements IncomeBO {

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public ArrayList<CustomDTO> income() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = queryDAO.income();
        ArrayList<CustomDTO> allIncome = new ArrayList<>();
        for (CustomEntity ent : all) {
            allIncome.add(new CustomDTO( ent.getOrderDate(),ent.getUnitPrice()));
        }
        return allIncome;
    }
    }

