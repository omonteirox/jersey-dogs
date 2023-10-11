package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.jersey_crud_api.database.DbContext;
import com.jersey_crud_api.models.Dog;
import com.jersey_crud_api.models.Tutor;

public class TutorService {
	private Connection connection;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public Tutor add(Tutor tutor) {
		try {
			connection = DbContext.Connect();
			String query = "insert into tutor (name) values (?)";
			preparedStatement = connection.prepareStatement(query, preparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, tutor.getName());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				tutor.setId(resultSet.getInt("id"));
			}
			return tutor;
			
		} catch (Exception e) {
			System.err.println("Erro ao adicionar Tutor -> " + e.getMessage());
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
	public List<Tutor> getAll(){
		List<Tutor> tutorList = new ArrayList<Tutor>();
		String query = "select * from tutor";
		Tutor tutor = new Tutor();
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				tutor = new Tutor();
				tutor.setId(resultSet.getInt(1));
				tutor.setName(resultSet.getString(2));
				tutor.setDogs(getDogsOfTutor(tutor.getId()));
				tutorList.add(tutor);
			}	
			
			return tutorList;
		} catch (Exception e) {
			System.err.println("Erro ao Listar tutores -> " + e.getMessage());
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
	
	public Tutor getById(Integer id) {
		String query = "select * from tutor where id = ?";
		Tutor tutor = new Tutor();
		try {
			List<Dog> dogs = getDogsOfTutor(id);
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				tutor.setId(resultSet.getInt(1));
				tutor.setName(resultSet.getString(2));
				tutor.setDogs(dogs);
				System.out.println(tutor.toString());
			}
			return tutor;
		} catch (Exception e) {
			System.err.println("Erro ao listar tutor " + e.getMessage());
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
			DbContext.Disconnect(connection);
		}
		return null;
	}
	public boolean delete(Integer id) {
		String query = "delete from tutor where id = ?";
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao deletar tutor" + e.getMessage());
			return false;
		}finally {
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.Disconnect(connection);
		}
	}
	public List<Dog> getDogsOfTutor(Integer id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Dog> dogs = new ArrayList<Dog>();
		String query = "select * from dog where tutor_id = ?";
		try {
			connection = DbContext.Connect();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Dog dog = new Dog();
				dog.setId(resultSet.getInt("id"));
				dog.setBirthDate(resultSet.getDate("birth_date"));
				dog.setName(resultSet.getString("name"));
				dog.setRace(resultSet.getString("race"));
				dog.setColor(resultSet.getString("color"));
				dog.setTutorId(resultSet.getInt("tutor_id"));
				dogs.add(dog);
			}
		} catch (Exception e) {
			System.err.println("Erro ao buscar dogs do tutor" + e.getMessage());
		}finally {
			DbContext.Disconnect(connection);
			DbContext.ClosePreparedStatement(preparedStatement);
			DbContext.CloseResultSet(resultSet);
		}
		return dogs;
	}
	
}
