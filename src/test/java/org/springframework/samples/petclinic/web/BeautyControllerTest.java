
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Beauty;
import org.springframework.samples.petclinic.service.BeautyService;
import org.springframework.test.web.servlet.MockMvc;

public class BeautyControllerTest {

	@Autowired
	private BeautyController	beautyController;

	@Autowired
	private BeautyService		beautyService;

	@Autowired
	private MockMvc				mockMvc;

	private Beauty				beauty;

	private static final int	TEST_BEAUTY_ID	= 1;

	//	@BeforeEach
	//	void setUp() {
	//		this.beauty = new Beauty();
	//		this.beauty.setId(5);
	//		this.beauty.setName("TestingName");
	//		this.beauty.setCapacity(10);
	//		this.beauty.setDate(LocalDate.of(2020, 12, 23));
	//		this.beauty.setPlace("place");
	//		this.beauty.setRewardMoney(800.00);
	//		this.beauty.setStatus("DRAFT");
	//		
	//		given(this.beautyService.findBeautyById(beautyId))
	//	}

}
