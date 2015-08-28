package de.akquinet.engineering.vaadin.vaangular.demo;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.akquinet.engineering.vaadin.vaangular.demo.wetter.Wetter;
import de.akquinet.engineering.vaadin.vaangular.demo.wetter.Wetter.WetterClickListener;

@Theme("valo")
@PreserveOnRefresh
public class VaangularUI extends UI {

	private static final long serialVersionUID = 1L;

	protected Wetter weatherInfo;
	protected Button javaSend;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	@Override
	protected void init(VaadinRequest request) {

		try {

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setMargin(true);
			mainLayout.setSpacing(true);
			Accordion accordion = new Accordion();

			weatherInfo = new Wetter();
			final int[] times = new int[] { 10, 12, 14, 16 };
			final String[] entries = new String[] {
					"<strong>10°</strong> sunny", "<strong>12°</strong> windy",
					"<strong>14°</strong> cold", "<strong>20°</strong> superb" };
			weatherInfo.setDaten(times, entries);
			weatherInfo.addClickListener(new WetterClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void click(int time, String entry) {
					showPopup(entry);
				}
			});
			weatherInfo.setButtonCaption("E-Mail (from angular)");
			accordion.addTab(weatherInfo, "Wetter-Demo");
			mainLayout.addComponent(accordion);

			javaSend = new Button();
			javaSend.setCaption("E-Mail (from Java)");
			javaSend.addClickListener(new ClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					int index = weatherInfo.getSliderPos();
					System.out.println("Button from w/in Java - value: "
							+ index);
					showPopup(entries[index]);
				}
			});
			mainLayout.addComponent(javaSend);

			setContent(mainLayout);
		} catch (Exception e) {
			throw new RuntimeException("some stupid error occured!", e);
		}
	}

	private void showPopup(String eintrag) {
		Window modalWin = new Window("E-Mail is being sent...");
		modalWin.setContent(new Label("<div style=\"margin: 10px; \">"
				+ "<h2>Season's greetings</h2>" + "<p>" + eintrag + "</p>"
				+ "</div>", ContentMode.HTML));
		modalWin.setModal(true);
		modalWin.setWidth("400px");
		modalWin.setHeight("250px");
		modalWin.center();
		UI.getCurrent().addWindow(modalWin);
	}

}
