package com.credera;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@RequestMapping("/map")
	public @ResponseBody String map() {
		return "map";
	}

	@RequestMapping("/mentors")
	public @ResponseBody MentorResponse[] mentors() {
		MentorResponse[] mentors = {
			new MentorResponse("Kelleigh Maroney", "kmaroney@credera.com", "Baylor University"),
			new MentorResponse("John Lutteringer", "jlutteringer@credera.com", "Baylor University"),
			new MentorResponse("Ali Shan Momin", "amomin@credera.com", "Texas A&M University"),
			new MentorResponse("Graeme Scruggs", "gscruggs@credera.com", "Southern Methodist University"),
			new MentorResponse("Trey Sedate", "tsedate@credera.com", "Baylor University"),
			new MentorResponse("Christopher Blewett", "cblewett@credera.com", "Baylor University")
		};
		return mentors;
	}
}
