package thomas.maze.model.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import thomas.maze.MainActivity;

/**
 * Created by gibtmirdas on 01.04.14.
 */
public class ShowDialog implements Runnable {

	Context c;
	private String title;
	private String msg;

	public ShowDialog(Context c, String title, String msg) {
		this.c = c;
		this.title = title;
		this.msg = msg;
	}

	@Override
	public void run() {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setTitle(title);
		builder.setMessage(msg);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog dial = builder.create();
		dial.setCancelable(false);
		dial.show();
	}
}
