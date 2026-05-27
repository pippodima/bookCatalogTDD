package com.dimartinoFilippo.app;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import com.dimartinoFilippo.view.swing.LibrarySwingView;

@RunWith(GUITestRunner.class)
public class LibrarySwingAppE2E extends AssertJSwingJUnitTestCase{
	
	@ClassRule
	public static final MongoDBContainer mongo =
		new MongoDBContainer(DockerImageName.parse("mongo:4.0.5"));
	
	private FrameFixture window;
	
	@Override
	protected void onSetUp() {
		System.setProperty("mongo.host", mongo.getHost());
		System.setProperty("mongo.port",
				String.valueOf(mongo.getFirstMappedPort()));
		
		window = new FrameFixture(robot(), GuiActionRunner.execute(() -> {
			LibrarySwingApp.main(new String[]{});
			
			return robot().finder()
					.findByType(LibrarySwingView.class);
		}));
		window.show();
	}
	
	@Override
	protected void onTearDown() {
		System.clearProperty("mongo.host");
		System.clearProperty("mongo.port");
	}

}
