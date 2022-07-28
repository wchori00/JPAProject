package tommy.spring.board;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class BoardServiceClient {
	public static void main(String[] args) {
		// EntityManager 생성
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("MyJPAProject");
		EntityManager manager = factory.createEntityManager();
		
		// Transaction 생성
		EntityTransaction tx = manager.getTransaction();
		
		try {
			// Transaction 시작
			tx.begin();
			
			Board board = new Board();
			board.setTitle("JPA 테스트");
			board.setWriter("손오공");
			board.setWriter("손오공");
			board.setContent("JPA 글 등록 테스트");
			
			// 글 등록
			manager.persist(board);
			
			// 글 목록 조회
			String jpasql = "select m from Board m order by m.seq desc";
			List<Board> boardList = manager.createQuery(jpasql, Board.class).getResultList();
			for(Board myBoard: boardList) {
				System.out.println("---> " + myBoard.toString());
			}
			
			// Transaction Commit
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			// Transaction RollBack
			tx.rollback();
		}finally {
			manager.clear();
		}
		factory.close();
	}
}
