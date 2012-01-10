class QuestionsController < ApplicationController
  # GET /questions
  def index
    @questions = Question.all
  end
  
  # GET /questions/1
  def show
    @question = Question.find(params[:id])
    @options = @question.options
    respond_to do |format|
      format.html
      format.json { render :json => @question }
    end
  end

  # GET /quizzes/1/questions/new
  def new
    @quiz = Quiz.find(params[:quiz_id])
    @question = Question.new(params[:question])
    if !is_user_admin
      redirect_to @question, :notice => "Cannot create unless admin."
    end
  end
  
  # GET /questions/1/edit
  def edit
    @question = Question.find(params[:id])
    if !is_user_admin
      redirect_to @question, :notice => "Cannot edit unless admin."
    end
  end
  
  # POST /quizzes/1/questions
  def create
    if is_user_admin
      @question = Question.new(params[:question])
      if @question.save
        redirect_to @question, :notice => "Question was successfully created."
      else
        render :action => "new"
      end
    else
      redirect_to quizzes_path
    end
  end
  
  # PUT /questions/1
  def update
    if is_user_admin
      
    else
    end
  end
  
  # DELETE /questions/1
  def destroy
    if is_user_admin
    else
    end
  end

end
