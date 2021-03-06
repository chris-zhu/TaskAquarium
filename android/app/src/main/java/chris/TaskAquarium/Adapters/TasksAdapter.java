package chris.TaskAquarium.Adapters;

import android.os.Build;
import android.support.v7.appcompat.BuildConfig;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chris.TaskAquarium.Models.MainTask;
import chris.TaskAquarium.Models.Task;
import chris.TaskAquarium.R;
import chris.TaskAquarium.ReminderSettingEnum;

/**
 * Created by chris on 8/11/16.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>{
	// TODO: adapter tied to each instance of a TaskList (recyclerview) used for population/drawing

	private List<MainTask> tasksList;

	public TasksAdapter() {
		this.tasksList = new ArrayList<>();
	}

	public TasksAdapter(List<MainTask> tasksList) {
		this.tasksList = tasksList;
	}

	@Override
	public int getItemCount() {
		return this.tasksList.size();
	}

	@Override
	public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
		MainTask task = this.tasksList.get(i);
		taskViewHolder.title.setText(task.getTitle());

		// TODO: ignore title for Main branch
		if (task.getCurrentBranch() != null &&
                task.getCurrentBranch().getNumTotalTasks() > 0) {
			taskViewHolder.branchTitle.setText(
					task.getCurrentBranch().getTitle());
			taskViewHolder.progressCurrStep.setText(String.valueOf(task.getCurrentBranch().getNumCompletedTasks()));
			taskViewHolder.progressTotalSteps.setText(String.valueOf(task.getCurrentBranch().getNumTotalTasks()));
		} else { // TODO: need to assign default branch if there is none, choose most close to completion?
			taskViewHolder.branchTitleContainer.setVisibility(View.GONE);
		}
	}

	@Override
	public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View itemView = LayoutInflater.
				from(viewGroup.getContext()).
				inflate(R.layout.task_card, viewGroup, false);

		return new TaskViewHolder(itemView);
	}

    // TODO: move following code into TasksManager
	public void addNewTask(MainTask task) {
		this.tasksList.add(task);
		this.notifyItemInserted(tasksList.size() - 1);
	}

    public boolean isTaskTitleAvailable(String taskTitle) {
        taskTitle = taskTitle.trim();

        for (MainTask task : tasksList) {
            if (task.getTitle().equalsIgnoreCase(taskTitle)) {
                return false;
            }
        }

        // exiting for loop means the title was not taken already
        return true;
    }


	public static class TaskViewHolder extends RecyclerView.ViewHolder {
		// Task card views
		private TextView title, subtitle, branchTitle, progressCurrStep, progressTotalSteps;

        private LinearLayout branchTitleContainer;

		public TaskViewHolder(View v) {
			super(v);
			this.title = (TextView) v.findViewById(R.id.task_title);
//			this.subtitle = (TextView) v.findViewById(R.id.task_subtitle);
			this.branchTitle = (TextView) v.findViewById(R.id.task_branch_title);
            this.branchTitleContainer = (LinearLayout) v.findViewById(R.id.task_branch_title_container);
			this.progressCurrStep = (TextView) v.findViewById(R.id.task_current_step);
			this.progressTotalSteps = (TextView) v.findViewById(R.id.task_total_steps);
		}
	}
}
